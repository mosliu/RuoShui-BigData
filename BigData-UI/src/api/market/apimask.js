import request from '@/utils/request'

export function pageApiMask (data) {
  return request({
    url: '/market/apiMasks/page',
    method: 'get',
    params: data
  })
}

export function getApiMask (id) {
  return request({
    url: '/market/apiMasks/' + id,
    method: 'get'
  })
}

export function delApiMask (id) {
  return request({
    url: '/market/apiMasks/' + id,
    method: 'delete'
  })
}

export function delApiMasks (ids) {
  return request({
    url: '/market/apiMasks/batch/' + ids,
    method: 'delete'
  })
}

export function addApiMask (data) {
  return request({
    url: '/market/apiMasks',
    method: 'post',
    data: data
  })
}

export function updateApiMask (data) {
  return request({
    url: '/market/apiMasks/' + data.id,
    method: 'put',
    data: data
  })
}
