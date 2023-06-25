import request from '@/utils/request'

export function listDataTable (data) {
  return request({
    url: '/tables/list',
    method: 'get',
    params: data
  })
}

export function pageDataTable (data) {
  return request({
    url: '/tables/page',
    method: 'get',
    params: data
  })
}

export function getDataTable (id) {
  return request({
    url: '/tables/' + id,
    method: 'get'
  })
}

export function delDataTable (id) {
  return request({
    url: '/tables/' + id,
    method: 'delete'
  })
}

export function delDataTables (ids) {
  return request({
    url: '/tables/batch/' + ids,
    method: 'delete'
  })
}

export function addDataTable (data) {
  return request({
    url: '/tables',
    method: 'post',
    data: data
  })
}

export function updateDataTable (data) {
  return request({
    url: '/tables/' + data.id,
    method: 'put',
    data: data
  })
}
