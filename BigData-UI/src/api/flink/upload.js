import request from '@/utils/flink/request'
import Qs from 'qs'

export function queryUploadFile(pageNum, pageSize, fileName) {
  return request({
    url: '/flink/uploadJar/queryUploadFile',
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    transformRequest: [function(data) { return Qs.stringify(data) }],
    data: {
      pageNum: pageNum,
      pageSize: pageSize,
      fileName: fileName
    }
  })
}
export function deleteFile(id) {
  return request({
    url: '/flink/uploadJar/deleteFile',
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    transformRequest: [function(data) { return Qs.stringify(data) }],
    data: {
      id: id
    }
  })
}

export function getFileJar() {
  return request({
    url: '/flink/uploadJar/getFileJar',
    method: 'post',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    transformRequest: [function(data) { return Qs.stringify(data) }]
  })
}


