import request from '@/utils/request'

// 查询数据库管理列表
export function listBase(query) {
  return request({
    url: '/base/base/list',
    method: 'get',
    params: query
  })
}

// 查询数据库管理详细
export function getBase(id) {
  return request({
    url: '/base/base/' + id,
    method: 'get'
  })
}

// 新增数据库管理
export function addBase(data) {
  return request({
    url: '/base/base',
    method: 'post',
    data: data
  })
}

// 修改数据库管理
export function updateBase(data) {
  return request({
    url: '/base/base',
    method: 'put',
    data: data
  })
}

// 删除数据库管理
export function delBase(id) {
  return request({
    url: '/base/base/' + id,
    method: 'delete'
  })
}

// 删除数据库管理
export function verification(passworde) {
  return request({
    url: '/base/base/verification',
    method: 'post',
    data: {
            passWorde: passworde
          }
  })
}
