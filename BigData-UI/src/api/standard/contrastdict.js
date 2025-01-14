import request from '@/utils/request'

export function pageContrastDict(data) {
  return request({
    url: '/standard/contrastDicts/page',
    method: 'get',
    params: data
  })
}

export function getContrastDict(id) {
  return request({
    url: '/standard/contrastDicts/' + id,
    method: 'get'
  })
}

export function delContrastDict(id) {
  return request({
    url: '/standard/contrastDicts/' + id,
    method: 'delete'
  })
}

export function delContrastDicts(ids) {
  return request({
    url: '/standard/contrastDicts/batch/' + ids,
    method: 'delete'
  })
}

export function addContrastDict(data) {
  return request({
    url: '/standard/contrastDicts',
    method: 'post',
    data: data
  })
}

export function updateContrastDict(data) {
  return request({
    url: '/standard/contrastDicts/' + data.id,
    method: 'put',
    data: data
  })
}
