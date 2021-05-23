package ru.tiutikova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tiutikova.dto.DetailGroupDto;
import ru.tiutikova.dto.SimpleDto;
import ru.tiutikova.dto.detail.*;
import ru.tiutikova.service.DetailsService;

import java.util.List;

@RestController
@RequestMapping("/api/details/")
public class DetailsController {

    private DetailsService detailsService;

    @Autowired
    public DetailsController(DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "getDetailGroupTree", method = RequestMethod.GET)
    public List<DetailGroupDto> getDetailGroupTree() {
        return detailsService.getDetailGroupTree();
    }

    @RequestMapping(value = "findInDetailGroupTree", method = RequestMethod.POST)
    public List<DetailGroupDto> findInDetailGroupTree(@RequestBody SearchDto dto) {
        return detailsService.findInDetailGroupTree(dto);
    }

    @RequestMapping(value = "getDetailInfoById", method = RequestMethod.POST)
    public List<VGroupDetailInfoDto> getDetailInfoById(@RequestBody SimpleDto dto) {
        return detailsService.getDetailInfoById(dto);
    }

    @RequestMapping(value = "getDetailInfo", method = RequestMethod.POST)
    public FullDetailInfo getDetailInfo(@RequestBody SimpleDto dto) {
        return detailsService.getDetailInfo(dto);
    }

    @RequestMapping(value = "getDetailGroupInfoById", method = RequestMethod.POST)
    public List<DetailGroupListDto> getDetailGroupInfoById(@RequestBody SimpleDto dto) {
        return detailsService.getDetailGroupInfoById(dto);
    }

    /**
     * Представление деталей на диаграмме.
     * @param dto
     * @return
     */
    @RequestMapping(value = "getDetailGroupInfoListById", method = RequestMethod.POST)
    public FullDetailGroupListDto getDetailGroupInfoListById(@RequestBody SimpleDto dto) {
        return detailsService.getDetailGroupInfoListById(dto);
    }

    @RequestMapping(value = "searchPath", method = RequestMethod.POST)
    public List<DetailInPictureDto> searchPath(@RequestBody SearchDto dto) {
        return detailsService.searchPath(dto);
    }


}
