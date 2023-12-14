package com.ruoshui.web.controller.bigdata;

import com.ruoshui.bigdata.entity.BaseResource;
import com.ruoshui.bigdata.mapper.BaseResourceMapper;
import com.ruoshui.bigdata.util.AESUtil;
import com.ruoshui.core.biz.model.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/base/resource")
@Api(tags = "基础建设-资源管理")
public class BaseResourceController {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private BaseResourceMapper baseResourceMapper;

	@ApiOperation("获取所有数据")
	@GetMapping("/list")
	@PreAuthorize("@ss.hasPermi('datax:resource:list')")
	public ReturnT<Map<String, Object>> selectList(
			@RequestParam(value = "current", required = false, defaultValue = "1") int current,
			@RequestParam(value = "size", required = false, defaultValue = "10") int size,
			@RequestParam(value = "name", required = false) String name) {
		// page list
		List<BaseResource> list = baseResourceMapper.findList((current - 1) * size,size,name);
		Map<String, Object> maps = new HashMap<>();
		maps.put("recordsTotal", list.size());    // 过滤后的总记录数
		maps.put("data", list);                    // 分页列表
		return new ReturnT<>(maps);
	}

	@ApiOperation("新增数据")
	@PostMapping("/add")
	@PreAuthorize("@ss.hasPermi('datax:resource:add')")
	public ReturnT<String> insert(HttpServletRequest request, @RequestBody BaseResource entity) {
		entity.setUpdate_time(sdf.format(new Date()));
		entity.setServerPassword(AESUtil.encrypt(entity.getServerPassword()));
		this.baseResourceMapper.save(entity);
		return ReturnT.SUCCESS;
	}

	@ApiOperation("修改数据")
	@PostMapping(value = "/update")
	@PreAuthorize("@ss.hasPermi('datax:resource:edit')")
	public ReturnT<String> update(@RequestBody BaseResource entity) {
		entity.setUpdate_time(sdf.format(new Date()));
		//查询元数据
		BaseResource byId = baseResourceMapper.getById(entity.getId());
		if(entity.getServerPassword().equals(byId.getServerPassword())){
			entity.setServerPassword(AESUtil.encrypt(AESUtil.decrypt(entity.getServerPassword())));
		}else{
			entity.setServerPassword(AESUtil.encrypt(entity.getServerPassword()));
		}
		baseResourceMapper.update(entity);
		return ReturnT.SUCCESS;
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ApiOperation("删除数据")
	@PreAuthorize("@ss.hasPermi('datax:resource:remove')")
	public ReturnT<String> delete(int id) {
		int result = baseResourceMapper.delete(id);
		return result != 1 ? ReturnT.FAIL : ReturnT.SUCCESS;
	}

	@RequestMapping(value = "/getResource", method = RequestMethod.POST)
	@ApiOperation("查询资源列表")
	@PreAuthorize("@ss.hasPermi('datax:resource:query')")
	public ReturnT<String> getResource() {
		List<BaseResource> result = baseResourceMapper.getResource();
		return new ReturnT(result);
	}

	@RequestMapping(value = "/getFileResource", method = RequestMethod.POST)
	@ApiOperation("查询资源列表")
	@PreAuthorize("@ss.hasPermi('datax:resource:query')")
	public ReturnT<String> getFileResource() {
		List<BaseResource> result = baseResourceMapper.getFileResource();
		return new ReturnT(result);
	}

}
