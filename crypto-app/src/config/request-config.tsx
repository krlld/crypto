export const config = localStorage.getItem('token')
	? { headers: { Authorization: 'Bearer ' + localStorage.getItem('token') } }
	: { headers: { Authorization: '' } };
