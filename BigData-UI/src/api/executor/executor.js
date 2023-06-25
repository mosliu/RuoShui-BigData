import request from '@/utils/request'

// 查询执行器管理列表
export function listExecutor(query) {
  return request({
    url: '/executor/executor/list',
    method: 'get',
    params: query
  })
}

// 查询执行器管理详细
export function getExecutor(id) {
  return request({
    url: '/executor/executor/' + id,
    method: 'get'
  })
}

// 新增执行器管理
export function addExecutor(data) {
  return request({
    url: '/executor/executor',
    method: 'post',
    data: data
  })
}

// 修改执行器管理
export function updateExecutor(data) {
  return request({
    url: '/executor/executor',
    method: 'put',
    data: data
  })
}

// 删除执行器管理
export function delExecutor(id) {
  return request({
    url: '/executor/executor/' + id,
    method: 'delete'
  })
}
