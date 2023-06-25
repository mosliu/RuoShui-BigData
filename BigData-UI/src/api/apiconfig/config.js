import request from '@/utils/request'

// 查询数据API列表
export function listConfig(query) {
  return request({
    url: '/apiconfig/config/list',
    method: 'get',
    params: query
  })
}

// 查询数据API详细
export function getConfig(id) {
  return request({
    url: '/apiconfig/config/' + id,
    method: 'get'
  })
}

// 新增数据API
export function addConfig(data) {
  return request({
    url: '/apiconfig/config',
    method: 'post',
    data: data
  })
}

// 修改数据API
export function updateConfig(data) {
  return request({
    url: '/apiconfig/config',
    method: 'put',
    data: data
  })
}

// 删除数据API
export function delConfig(id) {
  return request({
    url: '/apiconfig/config/' + id,
    method: 'delete'
  })
}
