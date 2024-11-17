import React, { useEffect, useState } from 'react';
import { Form, Input, Button, Avatar, message, Upload } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import axios from 'axios';
import { User } from '../types/User';
import { Profile } from '../types/Profile';
import { API_URL } from '../config/constants';
import { config } from '../config/request-config';
import { FileMetadata } from '../types/FileMetadata';

const ProfilePage: React.FC = () => {
	const [form] = Form.useForm();
	const [user, setUser] = useState<User | null>(null);
	const [loading, setLoading] = useState<boolean>(true);
	const [avatarId, setAvatarId] = useState<string | null>(null);

	useEffect(() => {
		const fetchUserData = async () => {
			try {
				const response = await axios.get<User>(`${API_URL}/auth-service/users/about-me`, config);
				setUser(response.data);
				form.setFieldsValue({
					name: response.data.name,
					lastname: response.data.lastname,
					avatarId: response.data.avatarId,
				});
				setAvatarId(response.data.avatarId);
			} catch (error) {
				message.error('Ошибка при загрузке данных пользователя');
			} finally {
				setLoading(false);
			}
		};

		fetchUserData();
	}, [form]);

	const handleAvatarUpload = async (file: File) => {
		const formData = new FormData();
		formData.append('file', file);

		try {
			const response = await axios.post<FileMetadata>(`${API_URL}/file-service/files`, formData, {
				headers: {
					'Content-Type': 'multipart/form-data',
				},
			});
			setAvatarId(response.data.id);
			message.success('Аватар успешно загружен!');
		} catch (error) {
			message.error('Ошибка при загрузке аватара');
		}
	};

	const onFinish = async (values: Profile) => {
		try {
			const updatedValues = { ...values, avatarId };
			await axios.put(`${API_URL}/auth-service/users`, updatedValues, config);
			message.success('Данные профиля успешно обновлены!');
			setUser((prev) => (prev ? { ...prev, ...updatedValues } : null));
		} catch (error) {
			message.error('Ошибка при обновлении данных профиля');
		}
	};

	if (loading) {
		return <div>Загрузка...</div>;
	}

	return (
		<div style={{ padding: '20px' }}>
			<h1>Профиль пользователя</h1>
			{user && (
				<div style={{ display: 'flex', alignItems: 'center' }}>
					<Avatar src={`${API_URL}/file-service/files/${avatarId}/download`} size={100} />
					<div style={{ marginLeft: '20px' }}>
						<h2>
							{user.name} {user.lastname}
						</h2>
						<p>Email: {user.email}</p>
					</div>
				</div>
			)}
			<Form form={form} onFinish={onFinish} layout="vertical" style={{ marginTop: '20px' }}>
				<Form.Item
					name="name"
					label="Имя"
					rules={[{ required: true, message: 'Пожалуйста, введите имя!' }]}
				>
					<Input />
				</Form.Item>
				<Form.Item
					name="lastname"
					label="Фамилия"
					rules={[{ required: true, message: 'Пожалуйста, введите фамилию!' }]}
				>
					<Input />
				</Form.Item>
				<Form.Item label="Аватар" extra="Загрузите изображение для аватара">
					<Upload showUploadList={false} beforeUpload={handleAvatarUpload} accept="image/*">
						<Button icon={<UploadOutlined />}>Загрузить аватар</Button>
					</Upload>
				</Form.Item>
				<Form.Item>
					<Button type="primary" htmlType="submit">
						Сохранить изменения
					</Button>
				</Form.Item>
			</Form>
		</div>
	);
};

export default ProfilePage;
