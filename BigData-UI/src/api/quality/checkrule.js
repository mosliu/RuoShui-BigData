import request from '@/utils/request'

export function listRuleType(data) {
  return request({
    url: '/quality/ruleTypes/list',
    method: 'get',
    params: data
  })
}

export function listRuleItem(data) {
  return request({
    url: '/quality/ruleItems/list',
    method: 'get',
    params: data
  })
}

export function listRuleLevel(data) {
  return request({
    url: '/quality/ruleLevels/list',
    method: 'get',
    params: data
  })
}

export function pageCheckRule(data) {
  return request({
    url: '/quality/checkRules/page',
    method: 'get',
    params: data
  })
}

export function getCheckRule(id) {
  return request({
    url: '/quality/checkRules/' + id,
    method: 'get'
  })
}

export function delCheckRule(id) {
  return request({
    url: '/quality/checkRules/' + id,
    method: 'delete'
  })
}

export function delCheckRules(ids) {
  return request({
    url: '/quality/checkRules/batch/' + ids,
    method: 'delete'
  })
}

export function addCheckRule(data) {
  return request({
    url: '/quality/checkRules',
    method: 'post',
    data: data
  })
}

export function updateCheckRule(data) {
  return request({
    url: '/quality/checkRules/' + data.id,
    method: 'put',
    data: data
  })
}
