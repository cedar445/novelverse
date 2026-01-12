import request from './request'

// get
export const getChaptersByBookId = async (bookId) => {
  const res = await request.get(`/chapters/${bookId}`)
  return res
}

export const getChapterContentById = async (id) => {
  return request.get(`/chapters/${id}/content`, {
    responseType: 'text', // ← 关键
  })
}
