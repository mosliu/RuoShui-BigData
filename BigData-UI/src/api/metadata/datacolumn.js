import request from '@/utils/request'

export function listDataColumn(data) {
  return request({
    url: '/columns/list',
    method: 'get',
    params: data
  })
}

export function pageDataColumn(data) {
  return request({
    url: '/columns/page',
    method: 'get',
    params: data
  })
}

export function getDataColumn(id) {
  return request({
    url: '/columns/' + id,
    method: 'get'
  })
}

export function delDataColumn(id) {
  return request({
    url: '/columns/' + id,
    method: 'delete'
  })
}

export function delDataColumns(ids) {
  return request({
    url: '/columns/batch/' + ids,
    method: 'delete'
  })
}

export function addDataColumn(data) {
  return request({
    url: '/columns',
    method: 'post',
    data: data
  })
}

export function updateDataColumn(data) {
  return request({
    url: '/columns/' + data.id,
    method: 'put',
    data: data
  })
}

export function getDataMetadataTree(level, data) {
  return request({
    url: '/columns/tree/' + level,
    method: 'get',
    params: data
  })
}
