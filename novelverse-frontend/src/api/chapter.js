import request from './request'

// get
export const getChaptersByBookId = async (bookId) => {
  const res = await request.get(`/chapters/${bookId}`)
  return res
}

export const getChapterContentById = async (id) => {
  const res = await request.get(`/chapters/${id}/content`)
  return res
}
