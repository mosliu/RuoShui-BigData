import request from '@/utils/request'

export function pageApiLog(data) {
  return request({
    url: '/market/apiLogs/page',
    method: 'get',
    params: data
  })
}

export function getApiLog(id) {
  return request({
    url: '/market/apiLogs/' + id,
    method: 'get'
  })
}

export function delApiLog(id) {
  return request({
    url: '/market/apiLogs/' + id,
    method: 'delete'
  })
}

export function delApiLogs(ids) {
  return request({
    url: '/market/apiLogs/batch/' + ids,
    method: 'delete'
  })
}
