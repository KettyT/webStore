package ru.tiutikova.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tiutikova.dao.entity.DetailGroupEntity;
import ru.tiutikova.dao.entity.VDetailInfoEntity;
import ru.tiutikova.dao.entity.VGroupDetailInfoEntity;
import ru.tiutikova.dao.repositories.DetailGroupRepository;
import ru.tiutikova.dao.repositories.VDetailInfoRepository;
import ru.tiutikova.dao.repositories.VGroupDetailInfoRepository;
import ru.tiutikova.dto.DetailGroupDto;
import ru.tiutikova.dto.SimpleDto;
import ru.tiutikova.dto.VDetailInfoDto;
import ru.tiutikova.dto.VGroupDetailInfoDto;

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
