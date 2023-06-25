import request from '@/utils/request'

export function getDictMapping(id) {
  return request({
    url: '/standard/mappings/' + id,
    method: 'get'
  })
}

export function dictAutoMapping(id) {
  return request({
    url: '/standard/mappings/auto/' + id,
    method: 'post'
  })
}

export function dictManualMapping(data) {
  return request({
    url: '/standard/mappings/manual',
    method: 'post',
    data: data
  })
}

export function dictCancelMapping(id) {
  return request({
    url: '/standard/mappings/cancel/' + id,
    method: 'post'
  })
}
