package ru.tiutikova.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tiutikova.dao.entity.VGroupDetailInfoEntity;
import ru.tiutikova.dao.entity.detail.*;
import ru.tiutikova.dao.repositories.DetailGroupRepository;
import ru.tiutikova.dao.repositories.detail.*;
import ru.tiutikova.dto.DetailGroupDto;
import ru.tiutikova.dto.SimpleDto;
import ru.tiutikova.dto.detail.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DetailsService {

    private DetailGroupRepository detailGroupRepository;

    private VGroupDetailInfoRepository groupDetailInfoRepository;

    private VDetailInfoRepository detailInfoRepository;

    private DetailGroupListRepository detailGroupListRepository;

    private DetailImagePositionRepository detailImagePositionRepository;

    private DetailsRepository detailsRepository;

    private DetailInPictureRepository detailInPictureRepository;

    @Autowired
    public DetailsService(DetailGroupRepository detailGroupRepository,
                          VGroupDetailInfoRepository groupDetailInfoRepository,
                          VDetailInfoRepository detailInfoRepository, DetailGroupListRepository detailGroupListRepository,
                          DetailImagePositionRepository detailImagePositionRepository, DetailsRepository detailsRepository,
                          DetailInPictureRepository detailInPictureRepository) {
        this.detailGroupRepository = detailGroupRepository;
        this.groupDetailInfoRepository = groupDetailInfoRepository;
        this.detailInfoRepository = detailInfoRepository;
        this.detailGroupListRepository = detailGroupListRepository;
        this.detailImagePositionRepository = detailImagePositionRepository;
        this.detailsRepository = detailsRepository;
        this.detailInPictureRepository = detailInPictureRepository;
    }

    private List<DetailGroupDto> reverseMapDetailGroupResult (List<DetailGroupEntity> detailGroupEntityList) {
        List<DetailGroupDto> result = new ArrayList<>();

        Map<Integer, DetailGroupDto> detailGroupDtoMap = new HashMap<>();

        for (int i = detailGroupEntityList.size() - 1; i > -1 ; i--) {
            DetailGroupEntity detailGroupEntity = detailGroupEntityList.get(i);

            DetailGroupDto detailGroupDto = new DetailGroupDto(detailGroupEntity);

            detailGroupDtoMap.put(detailGroupDto.getId(), detailGroupDto);

            if (detailGroupDto.getParentId() == null) {
                result.add(detailGroupDto);
            } else {
                detailGroupDtoMap.get(detailGroupDto.getParentId()).addChild(detailGroupDto);
            }
        }

        return result;
    }

    private List<DetailGroupDto> mapDetailGroupResult (List<DetailGroupEntity> detailGroupEntityList) {
        List<DetailGroupDto> result = new ArrayList<>();

        Map<Integer, DetailGroupDto> detailGroupDtoMap = new HashMap<>();

        for (DetailGroupEntity detailGroupEntity : detailGroupEntityList) {

            DetailGroupDto detailGroupDto = new DetailGroupDto(detailGroupEntity);

            detailGroupDtoMap.put(detailGroupDto.getId(), detailGroupDto);

            if (detailGroupDto.getLevel() == 0) {
                result.add(detailGroupDto);
            } else {
                detailGroupDtoMap.get(detailGroupDto.getParentId()).addChild(detailGroupDto);
            }
        }

        return result;
    }

    public List<DetailGroupDto> getDetailGroupTree() {

        List<DetailGroupEntity> detailGroupEntityList = detailGroupRepository.getDetailGroupListTree();

        return mapDetailGroupResult(detailGroupEntityList);
    }

    public List<DetailGroupDto> findInDetailGroupTree(SearchDto dto) {

        List<DetailInPictureDto> detailDtoList = searchPath(dto);

        List<Integer> idList = detailDtoList.stream().map((elm) -> {
            return elm.getId();
        }).collect(Collectors.toList());

        List<DetailGroupEntity> detailGroupEntityList = detailGroupRepository.getDetailGroupListTreeByDetailIds(idList);

        return reverseMapDetailGroupResult(detailGroupEntityList);
    }

    public List<VGroupDetailInfoDto> getDetailInfoById(SimpleDto dto) {
        List<VGroupDetailInfoDto> result = new ArrayList<>();
        List<VGroupDetailInfoEntity> groupDetailInfoEntityList = groupDetailInfoRepository.getAllByDetailGroupId(dto.getId());

        for (VGroupDetailInfoEntity entity : groupDetailInfoEntityList) {
            result.add(new VGroupDetailInfoDto(entity));
        }

        return result;
    }

    public FullDetailInfo getDetailInfo(SimpleDto dto) {

        DetailsEntity detailEntry = detailsRepository.getById(dto.getId());

        FullDetailInfo result = new FullDetailInfo(detailEntry);

        List<VDetailInfoEntity> detailInfoEntityList = detailInfoRepository.getAllByProducerDetailCodeOrderById(detailEntry.getCode());

        for (VDetailInfoEntity detailInfoEntity : detailInfoEntityList) {
            if (Objects.equals(detailInfoEntity.getArticle(), detailEntry.getOriginalArticle())) {
                result.getDetailInfoDtoList().add(new VDetailInfoDto(detailInfoEntity));
            } else {
                result.getAnalogInfoDtoList().add(new VDetailInfoDto(detailInfoEntity));
            }
        }

        return result;
    }

    public List<DetailGroupListDto> getDetailGroupInfoById(SimpleDto dto) {

        List<DetailGroupListEntity> detailGroupListEntityList = detailGroupListRepository.getByDetailGroupIdOrderById(dto.getId());
        List<Integer> detailGroupListId = new ArrayList<>();
        Map<Integer, DetailGroupListDto> detailGroupListDtoMap = new HashMap<>();

        List<DetailGroupListDto> detailGroupListDtoList = new ArrayList<>();

        for (DetailGroupListEntity entity : detailGroupListEntityList) {
            detailGroupListId.add(entity.getId());

            DetailGroupListDto detailGroupListDto = new DetailGroupListDto(entity);

            detailGroupListDtoMap.put(entity.getId(), detailGroupListDto);
            detailGroupListDtoList.add(detailGroupListDto);
        }

        // todo
        List<DetailsEntity> detailInfoDtoList = detailsRepository.getAllByDetailGroupListIdInOrderById(detailGroupListId);

        for (DetailsEntity entity : detailInfoDtoList) {
            DetailGroupListDto detailGroupListDto = detailGroupListDtoMap.get(entity.getDetailGroupListId());

            detailGroupListDto.getDetailDtoList().add(new DetailDto(entity));
        }

        return detailGroupListDtoList;
    }

    public FullDetailGroupListDto getDetailGroupInfoListById (SimpleDto dto) {

        DetailGroupListEntity detailGroupListEntity = detailGroupListRepository.getById(dto.getId());
        List<Integer> detaiList = new ArrayList<>();
        detaiList.add(detailGroupListEntity.getId());

        List<DetailsEntity> detailInfoDtoList = detailsRepository.getAllByDetailGroupListIdInOrderById(detaiList);

        List<DetailImagePositionEntity> detailImagePositionEntityList = detailImagePositionRepository.getAllByDetailGroupListIdOrderById(detailGroupListEntity.getId());

        FullDetailGroupListDto result = new FullDetailGroupListDto(detailGroupListEntity);

        for (DetailsEntity detailsEntity : detailInfoDtoList) {
            result.getDetailDtoList().add(new DetailDto(detailsEntity));
        }

        for (DetailImagePositionEntity entity : detailImagePositionEntityList) {
            result.getDetailImagePositionDtoList().add(new DetailImagePositionDto(entity));
        }

        return result;
    }

    public List<DetailInPictureDto> searchPath(SearchDto dto) {
        List<DetailsEntity> detailsEntityList = detailsRepository.getAllByNameIsLike("%" + dto.getQuery() + "%");

        List<DetailInPictureDto> detailInPictureDtoList = new ArrayList<>();

        List<Integer> detailIdList = detailsEntityList.stream().map((entity) -> {
            return entity.getId();
        }).collect(Collectors.toList());

        List<DetailInPictureEntity> detailInPictureEntityList = detailInPictureRepository.getAllDetailInPicture(detailIdList);

        for (DetailsEntity detailsEntity : detailsEntityList) {
            DetailInPictureEntity objectToFind = new DetailInPictureEntity();
            objectToFind.setId(detailsEntity.getId());

            int index = Collections.binarySearch(detailInPictureEntityList, objectToFind, (elm1, elm2) -> {
                return elm1.getId() - elm2.getId();
            });

            if (index < 0) {
                continue;
            }

            String code = detailInPictureEntityList.get(index).getProducerCode();

            DetailInPictureDto detailInPictureDto = new DetailInPictureDto(detailsEntity);

            for (DetailInPictureEntity detailInPictureEntity : detailInPictureEntityList) {
                if (Objects.equals(detailInPictureEntity.getProducerCode(), code)) {

                    if (detailInPictureEntity.getIsOriginal() == 1) {
                        detailInPictureDto.getAliasList().add(0, detailInPictureEntity.getName());
                    } else {
                        detailInPictureDto.getAliasList().add(detailInPictureEntity.getName());
                    }
                }
            }

            detailInPictureDtoList.add(detailInPictureDto);
        }



        /*return detailsEntityList.stream().map((entity) -> {
            return new DetailDto(entity);
        }).collect(Collectors.toList());*/

        return detailInPictureDtoList;
    }


}
