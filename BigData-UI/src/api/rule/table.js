import request from '@/utils/request'

// 查询数据表列表列表
export function listTable(query) {
  return request({
    url: '/dataassets/table/list',
    method: 'get',
    params: query
  })
}

// 查询数据表列表列表
export function Fieldlist(query) {
  return request({
    url: '/dataassets/table/Fieldlist',
    method: 'get',
    params: query
  })
}

// 查询数据表列表详细
export function getTable(id) {
  return request({
    url: '/dataassets/table/' + id,
    method: 'get'
  })
}

// 新增数据表列表
export function addTable(data) {
  return request({
    url: '/dataassets/table',
    method: 'post',
    data: data
  })
}

// 修改数据表列表
export function updateTable(data) {
  return request({
    url: '/dataassets/table',
    method: 'put',
    data: data
  })
}

// 删除数据表列表
export function delTable(id) {
  return request({
    url: '/dataassets/table/' + id,
    method: 'delete'
  })
}
// 获取数据库
export function getdataList() {
  return request({
    url: '/dataassets/table/getdataList',
    method: 'post'
  })
}

// 获取表
export function gettableList(id) {
  return request({
    url: '/dataassets/table/gettableList',
    method: 'post',
    data: {
      id: id
    }
  })
}

// 自定义sql查询
export function executeQuerySql(data) {
  return request({
    url: '/dataassets/table/executeQuerySql',
    method: 'post',
    data: data
  })
}

// 自定义sql查询
export function getmetadataTree(id) {
  return request({
    url: '/dataassets/table/getmetadataTree',
    method: 'post',
    data: {
      baseId: id
    }
  })
}

// 获取数据库中的表信息
export function Syndatabase() {
  return request({
    url: '/dataassets/table/Syndatabase',
    method: 'post'
  })
}

// 获取数据库中的表信息
export function Synfielddatabase(id) {
  return request({
    url: '/dataassets/table/Synfielddatabase',
    method: 'post',
    data: {
      id: id
    }
  })
}
