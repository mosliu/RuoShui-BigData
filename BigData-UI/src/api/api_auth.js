import request from '@/utils/request'

// datax用户api

export function getList(params) {
  return request({
    url: '/api/bigData/user/pageList',
    method: 'get',
    params,
  })
}

export function updateUser(data) {
  return request({
    url: '/api/bigData/user/update',
    method: 'post',
    data,
  })
}

export function createUser(data) {
  return request({
    url: '/api/bigData/user/add',
    method: 'post',
    data,
  })
}

export function deleteUser(id) {
  return request({
    url: '/api/bigData/user/remove?id=' + id,
    method: 'post',
  })
}

export function resourceAdd(data) {
  return request({
    url: '/api/apiAuth/add',
    method: 'post',
    data,
  })
}
export function resourceUpdate(data) {
  return request({
    url: '/api/bigData/apiAuth/update',
    method: 'post',
    data,
  })
}

export function resourceDelete(id) {
  return request({
    url: '/api/bigData/apiAuth/remove?id=' + id,
    method: 'post',
  })
}

export function getResourceList(params) {
  return request({
    url: '/api/bigData/apiAuth/list',
    method: 'get',
    params,
  })
}
