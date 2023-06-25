import request from '@/utils/request'

export function devSqlAdd(data) {
  return request({
    url: 'api/bigData/devJar/add',
    method: 'post',
    data,
  })
}

export function devSqlUpdate(data) {
  return request({
    url: '/api/bigData/devJar/update',
    method: 'post',
    data,
  })
}

export function devTaskExecute(params) {
  params = Object.assign({ sql_text: params.sql_text }, params || {})
  return request({
    url: '/api/bigData/deployTask/checkSQL',
    method: 'get',
    params,
  })
}

export function devTaskUpload(data, onProgress = null) {
  return request({
    url: '/api/bigData/devTask/upload',
    method: 'post',
    data,
    onUploadProgress: (progressEvent) => {
      const process = ((progressEvent.loaded / progressEvent.total) * 100) | 0
      console.log(`上传进度：${process}%`)
      onProgress?.(process)
    },
  })
}

export function devTaskList(params) {
  params = Object.assign({ type: 'SQL' }, params || {})
  return request({
    url: '/api/bigData/devTask/list',
    method: 'get',
    params,
  })
}
