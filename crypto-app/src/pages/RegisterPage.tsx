import React, { useState } from 'react';
import { Form, Input, Button, message } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import axios from 'axios';
import { API_URL } from '../config/constants';
import { User } from '../types/User';

const RegistrationPage: React.FC = () => {
	const [form] = Form.useForm();
	const [avatarId, setAvatarId] = useState<string | null>(null);
	const [avatarUrl, setAvatarUrl] = useState<string | null>(null);

	const handleUpload = async (file: File): Promise<void> => {
		const formData = new FormData();
		formData.append('file', file);

		try {
			const response = await axios.post(`${API_URL}/file-service/files`, formData, {
				headers: {
					'Content-Type': 'multipart/form-data',
				},
			});
			setAvatarId(response.data.id);
			const url = URL.createObjectURL(file);
			setAvatarUrl(url);
			message.success('Аватар загружен успешно!');
		} catch (error) {
			message.error('Ошибка загрузки аватара.');
		}
	};

	const onFinish = async (values: User) => {
		if (!avatarId) {
			message.error('Пожалуйста, загрузите аватар.');
			return;
		}

		const userData = {
			...values,
			avatarId: avatarId,
		};

		try {
			await axios.post(`${API_URL}/auth-service/auth/register`, userData);
			message.success('Регистрация успешна!');
		} catch (error) {
			message.error('Ошибка регистрации.');
		}
	};

	return (
		<div style={{ maxWidth: 400, margin: 'auto', textAlign: 'center' }}>
			<h2>Регистрация</h2>
			<div style={{ marginBottom: 16 }}>
				<div
					style={{
						width: 100,
						height: 100,
						borderRadius: '50%',
						backgroundColor: avatarUrl ? 'transparent' : '#d9d9d9',
						overflow: 'hidden',
						margin: '0 auto',
					}}
				>
					{avatarUrl && (
						<img
							src={avatarUrl}
							alt="Avatar"
							style={{
								width: '100%',
								height: '100%',
								borderRadius: '50%',
								objectFit: 'cover',
							}}
						/>
					)}
				</div>
			</div>
			<Form form={form} onFinish={onFinish}>
				<Form.Item
					name="email"
					rules={[{ required: true, type: 'email', message: 'Введите корректный email!' }]}
				>
					<Input placeholder="Email" />
				</Form.Item>
				<Form.Item
					name="password"
					rules={[{ required: true, min: 4, message: 'Пароль должен быть минимум 4 символов!' }]}
				>
					<Input.Password placeholder="Пароль" />
				</Form.Item>
				<Form.Item name="name" rules={[{ required: true, message: 'Введите имя!' }]}>
					<Input placeholder="Имя" />
				</Form.Item>
				<Form.Item name="lastname" rules={[{ required: true, message: 'Введите фамилию!' }]}>
					<Input placeholder="Фамилия" />
				</Form.Item>
				<Form.Item>
					<input
						id="upload"
						type="file"
						style={{ display: 'none' }}
						onChange={(e) => {
							if (e.target.files) {
								handleUpload(e.target.files[0]);
							}
						}}
					/>
					<Button
						icon={<UploadOutlined />}
						style={{ width: '100%' }}
						onClick={() => document.getElementById('upload')?.click()}
					>
						Загрузить аватар
					</Button>
				</Form.Item>
				<Form.Item>
					<Button type="primary" htmlType="submit" style={{ width: '100%' }}>
						Зарегистрироваться
					</Button>
				</Form.Item>
			</Form>
		</div>
	);
};

export default RegistrationPage;
