package ru.tiutikova.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.tiutikova.dto.DetailGroupDto;
import ru.tiutikova.dto.SimpleDto;
import ru.tiutikova.dto.VDetailInfoDto;
import ru.tiutikova.dto.VGroupDetailInfoDto;
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

    @RequestMapping(value = "getDetailInfoById", method = RequestMethod.POST)
    public List<VGroupDetailInfoDto> getDetailInfoById(@RequestBody SimpleDto dto) {
        return detailsService.getDetailInfoById(dto);
    }

    @RequestMapping(value = "getDetailInfo", method = RequestMethod.POST)
    public VDetailInfoDto getDetailInfo(@RequestBody SimpleDto dto) {
        return detailsService.getDetailInfo(dto);
    }


}
