import request from '@/utils/request'

export function pageCheckLog(data) {
  return request({
    url: '/quality/scheduleLogs/page',
    method: 'get',
    params: data
  })
}
