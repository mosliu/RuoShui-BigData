import request from '@/utils/request'

// dashborad

export function chartInfo(data) {
  return request({
    url: '/dataxapi/chartInfo',
    method: 'post',
    data,
  })
}
export function getInfo(params) {
  return request({
    url: '/dataxapi/index',
    method: 'get',
    params,
  })
}
export function getFindData(params) {
  return request({
    url: '/api/bigData/apiConfig/findData',
    method: 'get',
    params,
  })
}
export function getOverview() {
  return request({
    url: '/dataxapi/overview',
    method: 'get'
  })
}
