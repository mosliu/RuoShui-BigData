import request from '@/utils/request'

export function refreshDict() {
  return request({
    url: '/standard/dicts/refresh',
    method: 'get'
  })
}

export function listDataDictType(data) {
  return request({
    url: '/standard/types/list',
    method: 'get',
    params: data
  })
}

export function addDataDictType(data) {
  return request({
    url: '/standard/types',
    method: 'post',
    data: data
  })
}

export function updateDataDictType(data) {
  return request({
    url: '/standard/types/' + data.id,
    method: 'put',
    data: data
  })
}

export function delDataDictType(id) {
  return request({
    url: '/standard/types/' + id,
    method: 'delete'
  })
}

export function pageDataDict(data) {
  return request({
    url: '/standard/dicts/page',
    method: 'get',
    params: data
  })
}

export function getDataDict(id) {
  return request({
    url: '/standard/dicts/' + id,
    method: 'get'
  })
}

export function delDataDict(id) {
  return request({
    url: '/standard/dicts/' + id,
    method: 'delete'
  })
}

export function delDataDicts(ids) {
  return request({
    url: '/standard/dicts/batch/' + ids,
    method: 'delete'
  })
}

export function addDataDict(data) {
  return request({
    url: '/standard/dicts',
    method: 'post',
    data: data
  })
}

export function updateDataDict(data) {
  return request({
    url: '/standard/dicts/' + data.id,
    method: 'put',
    data: data
  })
}
