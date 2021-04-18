package ru.tutikova.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tutikova.dao.entity.DetailGroupEntity;
import ru.tutikova.dao.entity.VDetailInfoEntity;
import ru.tutikova.dao.entity.VGroupDetailInfoEntity;
import ru.tutikova.dao.repositories.DetailGroupRepository;
import ru.tutikova.dao.repositories.VDetailInfoRepository;
import ru.tutikova.dao.repositories.VGroupDetailInfoRepository;
import ru.tutikova.dto.DetailGroupDto;
import ru.tutikova.dto.SimpleDto;
import ru.tutikova.dto.VDetailInfoDto;
import ru.tutikova.dto.VGroupDetailInfoDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DetailsService {

    private DetailGroupRepository detailGroupRepository;

    private VGroupDetailInfoRepository groupDetailInfoRepository;

    private VDetailInfoRepository detailInfoRepository;

    @Autowired
    public DetailsService(DetailGroupRepository detailGroupRepository,
                          VGroupDetailInfoRepository groupDetailInfoRepository,
                          VDetailInfoRepository detailInfoRepository) {
        this.detailGroupRepository = detailGroupRepository;
        this.groupDetailInfoRepository = groupDetailInfoRepository;
        this.detailInfoRepository = detailInfoRepository;
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

    public VDetailInfoDto getDetailInfo(SimpleDto dto) {
        VDetailInfoEntity entity = detailInfoRepository.getById(dto.getId());

        return new VDetailInfoDto(entity);
    }



}
