import request from '@/utils/request'

export function listRuleType(data) {
  return request({
    url: '/quality/ruleTypes/report/list',
    method: 'get',
    params: data
  })
}

export function pageCheckReport(data) {
  return request({
    url: '/quality/checkReports/page',
    method: 'get',
    params: data
  })
}

export function getReportBySource(data) {
  return request({
    url: '/quality/checkReports/getReportBySource',
    method: 'get',
    params: data
  })
}

export function getReportByType(data) {
  return request({
    url: '/quality/checkReports/getReportByType',
    method: 'get',
    params: data
  })
}

export function getReportDetail(data) {
  return request({
    url: '/quality/checkReports/getReportDetail',
    method: 'get',
    params: data
  })
}
