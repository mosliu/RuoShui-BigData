import request from '@/utils/request'

// datax用户api

export function getList(params) {
  return request({
    url: '/api/user/pageList',
    method: 'get',
    params
  })
}

export function updateUser(data) {
  return request({
    url: '/api/user/update',
    method: 'post',
    data
  })
}

export function createUser(data) {
  return request({
    url: '/api/user/add',
    method: 'post',
    data
  })
}

export function deleteUser(id) {
  return request({
    url: '/api/user/remove?id=' + id,
    method: 'post'
  })
}

export function resourceAdd(data) {
  return request({
    url: '/api/base/resource/add',
    method: 'post',
    data
  })
}
export function resourceUpdate(data) {
  return request({
    url: '/api/base/resource/update',
    method: 'post',
    data
  })
}

export function resourceDelete(id) {
  return request({
    url: '/api/apiConfig/remove?id=' + id,
    method: 'post'
  })
}

export function getLogList(params) {
  return request({
    url: '/api/log/list',
    method: 'get',
    params
  })
}