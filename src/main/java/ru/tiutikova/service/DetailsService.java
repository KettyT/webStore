package ru.tiutikova.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    public DetailsService(DetailGroupRepository detailGroupRepository,
                          VGroupDetailInfoRepository groupDetailInfoRepository,
                          VDetailInfoRepository detailInfoRepository, DetailGroupListRepository detailGroupListRepository,
                          DetailImagePositionRepository detailImagePositionRepository, DetailsRepository detailsRepository) {
        this.detailGroupRepository = detailGroupRepository;
        this.groupDetailInfoRepository = groupDetailInfoRepository;
        this.detailInfoRepository = detailInfoRepository;
        this.detailGroupListRepository = detailGroupListRepository;
        this.detailImagePositionRepository = detailImagePositionRepository;
        this.detailsRepository = detailsRepository;
    }

    public List<DetailGroupDto> getDetailGroupTree() {

        List<DetailGroupDto> result = new ArrayList<>();

        Map<Integer, DetailGroupDto> detailGroupDtoMap = new HashMap<>();

        List<DetailGroupEntity> detailGroupEntityList = detailGroupRepository.getDetailGroupListTree();

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

    public List<DetailDto> searchPath(@RequestBody SearchDto dto) {
        List<DetailsEntity> detailsEntityList = detailsRepository.getAllByNameIsLike("%" + dto.getQuery() + "%");

        return detailsEntityList.stream().map((entity) -> {
            return new DetailDto(entity);
        }).collect(Collectors.toList());
    }


}
