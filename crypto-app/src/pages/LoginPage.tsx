import React from 'react';
import axios from 'axios';
import { Form, Input, Button, Typography, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import { API_URL } from '../config/constants';
import { Login } from '../types/Login';

const { Title } = Typography;

const LoginPage: React.FC = () => {
	const navigate = useNavigate();

	const onFinish = async (values: Login) => {
		try {
			const response = await axios.post(`${API_URL}/auth-service/auth/authenticate`, {
				email: values.email,
				password: values.password,
			});

			const token = response.data;
			localStorage.setItem('token', token);

			console.log('Response:', response.data);
			message.success('Успешный вход!');
			navigate('/');
		} catch (error) {
			console.error('Error during authentication:', error);
			message.error('Ошибка авторизации. Проверьте ваши данные.');
		}
	};

	const onFinishFailed = (errorInfo: any) => {
		console.log('Failed:', errorInfo);
		message.error('Пожалуйста, исправьте ошибки в форме.');
	};

	const handleRegister = () => {
		navigate('/register');
	};

	return (
		<div style={{ maxWidth: 400, margin: 'auto', padding: '50px' }}>
			<Title level={2} style={{ textAlign: 'center' }}>
				Вход в систему
			</Title>
			<Form
				name="login"
				initialValues={{ remember: true }}
				onFinish={onFinish}
				onFinishFailed={onFinishFailed}
			>
				<Form.Item
					label="Email"
					name="email"
					rules={[
						{ required: true, message: 'Пожалуйста, введите ваш email!' },
						{ type: 'email', message: 'Неверный формат email!' },
					]}
				>
					<Input />
				</Form.Item>

				<Form.Item
					label="Пароль"
					name="password"
					rules={[{ required: true, message: 'Пожалуйста, введите ваш пароль!' }]}
				>
					<Input.Password />
				</Form.Item>

				<Form.Item>
					<Button type="primary" htmlType="submit" block>
						Войти
					</Button>
				</Form.Item>

				<Form.Item>
					<Button type="link" onClick={handleRegister} block>
						Зарегистрироваться
					</Button>
				</Form.Item>
			</Form>
		</div>
	);
};

export default LoginPage;
