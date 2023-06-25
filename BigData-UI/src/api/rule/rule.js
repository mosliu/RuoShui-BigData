import request from '@/utils/request'

// 查询数仓规则管理列表
export function listRule(query) {
  return request({
    url: '/rule/rule/list',
    method: 'get',
    params: query
  })
}

// 查询数仓规则管理详细
export function getRule(id) {
  return request({
    url: '/rule/rule/' + id,
    method: 'get'
  })
}

// 新增数仓规则管理
export function addRule(data) {
  return request({
    url: '/rule/rule',
    method: 'post',
    data: data
  })
}

// 修改数仓规则管理
export function updateRule(data) {
  return request({
    url: '/rule/rule',
    method: 'put',
    data: data
  })
}

// 删除数仓规则管理
export function delRule(id) {
  return request({
    url: '/rule/rule/' + id,
    method: 'delete'
  })
}

// 获取数据源
export function getdataSource() {
  return request({
    url: '/rule/rule/getdataSource',
    method: 'post'
  })
}

// 获取有库数据源
export function getdataSourceAll() {
  return request({
    url: '/rule/rule/getdataSourceAll',
    method: 'post'
  })
}
