import request from './http';

const BASE_URL = 'http://localhost:40010'

export default {
  BASE_URL,
  addIngredient(cookBook) {
    return request.requestPost(BASE_URL + '/api/eat/add/ingredient', cookBook)
  },
  addFoodMenu(menu, str) {
    return request.requestPost(BASE_URL + '/api/eat/add/menu?ingredients=' + str, menu)
  },
  addMenuStep(menuId, str) {
    return request.requestGet(BASE_URL + '/api/eat/add/step?menuId=' + menuId + '&content=' + str)
  },
  ingredientList(currentPage, pageSize) {
    return request.requestGet(BASE_URL + '/api/eat/list/ingredient?currentPage=' + currentPage + '&pageSize=' + pageSize)
  },
  deleteCookBook(id) {
    return request.requestDelete(BASE_URL + '/api/eat/delete/ingredient?id=' + id)
  },
  deleteMenu(id) {
    return request.requestDelete(BASE_URL + '/api/eat/delete/menu?id=' + id)
  },
  listAllCookBook() {
    return request.requestGet(BASE_URL + '/api/eat/list/all/ingredient')
  },
  menuList(currentPage, pageSize) {
    return request.requestGet(BASE_URL + '/api/eat/list/menu?currentPage=' + currentPage + '&pageSize=' + pageSize)
  },
  addIngredients(str) {
    return request.requestGet(BASE_URL + '/api/eat/ingredient/menu/add?ingredients=' + str)
  }
}