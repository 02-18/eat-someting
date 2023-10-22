const registerApi = '/api/user/register'
const loginApi = '/api/user/login'
const userInfoApi = '/api/user/info?username='
const menuListApi = '/api/eat/list/all/menu'
const searchMenuAPi = '/api/eat/search/menu?name='
const menuInfoApi = '/api/eat/info/menu?id='
const addEnshrine = '/api/eat/add/enshrine?'
const deleteEnshrine = '/api/eat/delete/enshrine?'
const getStatusEnshrine = '/api/eat/status/enshrine?'
const listEnshrine = '/api/eat/list/enshrine?username='
const listIngredient = '/api/eat/list/all/ingredient'
const recommendAPi = '/api/eat/recommend?ingredientsId='
const getMenuStepAPi = '/api/eat/info/step?menuId='
const updateUserAvatar = '/api/user/update?avatar='
export default {
	registerApi,
	loginApi,
	userInfoApi,
	menuListApi,
	searchMenuAPi,
	menuInfoApi,
	addEnshrine,
	deleteEnshrine,
	getStatusEnshrine,
	listEnshrine,
	listIngredient,
	recommendAPi,
	getMenuStepAPi,
	updateUserAvatar
}