import request from '@/utils/request'

export function getApiCall(url, header, data) {
  return request({
    url: '/inner/data/api',
    method: 'get',
    headers: header,
    params: data
  })
}

export function postApiCall(url, header, data) {
  return request({
    url: '/inner/data/api',
    method: 'post',
    headers: header,
    data: data
  })
}
