import React, { useEffect, useState } from 'react';
import { Form, Input, Button, List, Select, message, Pagination, Spin } from 'antd';
import axios from 'axios';
import { Role } from '../types/Role';
import { Authority } from '../types/Authority';
import { Page } from '../types/Page';
import { config } from '../config/request-config';
import { API_URL } from '../config/constants';

const { Option } = Select;

const RolesPage: React.FC = () => {
	const [roles, setRoles] = useState<Role[]>([]);
	const [authorities, setAuthorities] = useState<Authority[]>([]);
	const [loading, setLoading] = useState<boolean>(true);
	const [form] = Form.useForm();
	const [currentPage, setCurrentPage] = useState<number>(0);
	const [totalRoles, setTotalRoles] = useState<number>(0);
	const [authoritiesLoading, setAuthoritiesLoading] = useState<boolean>(false);
	const [currentAuthoritiesPage, setCurrentAuthoritiesPage] = useState<number>(0);
	const [hasMoreAuthorities, setHasMoreAuthorities] = useState<boolean>(true);
	const [loadedAuthorityIds, setLoadedAuthorityIds] = useState<Set<number | undefined>>(new Set());
	const [editingRoleId, setEditingRoleId] = useState<number | null>(null);

	useEffect(() => {
		const fetchRoles = async () => {
			setLoading(true);
			try {
				const response = await axios.get<Page<Role>>(
					`${API_URL}/auth-service/roles?page=${currentPage}`,
					config
				);
				setRoles(response.data.content);
				setTotalRoles(response.data.page.totalElements);
			} catch (error) {
				message.error('Ошибка при загрузке ролей');
			} finally {
				setLoading(false);
			}
		};

		fetchRoles();
	}, [currentPage]);

	const loadAuthorities = async () => {
		if (!hasMoreAuthorities) return;

		setAuthoritiesLoading(true);
		try {
			const response = await axios.get<Page<Authority>>(
				`${API_URL}/auth-service/authorities?page=${currentAuthoritiesPage}`,
				config
			);
			const newAuthorities = response.data.content.filter(
				(authority) => !loadedAuthorityIds.has(authority.id)
			);

			if (newAuthorities.length > 0) {
				setAuthorities((prev) => [...prev, ...newAuthorities]);
				newAuthorities.forEach((authority) => loadedAuthorityIds.add(authority.id));
				setLoadedAuthorityIds(new Set(loadedAuthorityIds));
			}

			if (response.data.content.length === 0) {
				setHasMoreAuthorities(false);
			}
		} catch (error) {
			message.error('Ошибка при загрузке прав');
		} finally {
			setAuthoritiesLoading(false);
		}
	};

	const onFinish = async (values: Role) => {
		try {
			if (editingRoleId) {
				const response = await axios.put<Role>(
					`${API_URL}/auth-service/roles/${editingRoleId}`,
					values,
					config
				);
				message.success('Роль успешно обновлена!');
				setRoles((prev) =>
					prev.map((role) => (role.id === editingRoleId ? { ...role, ...response.data } : role))
				);
			} else {
				const response = await axios.post<Role>(`${API_URL}/auth-service/roles`, values, config);
				message.success('Роль успешно создана!');
				setRoles((prev) => [...prev, { ...response.data }]);
			}
			form.resetFields();
			setEditingRoleId(null);
		} catch (error) {
			message.error('Ошибка при создании или обновлении роли');
		}
	};

	const handleEdit = (role: Role) => {
		form.setFieldsValue({
			name: role.name,
			description: role.description,
			authorityIds: role.authorities?.map((auth) => auth.id) || [],
		});
		setEditingRoleId(role.id != null ? role.id : null);
	};

	const handleDelete = async (roleId: number) => {
		try {
			await axios.delete(`${API_URL}/auth-service/roles/${roleId}`, config);
			message.success('Роль успешно удалена!');
			setRoles((prev) => prev.filter((role) => role.id !== roleId));
		} catch (error) {
			message.error('Ошибка при удалении роли');
		}
	};

	const handleScroll = (e: React.UIEvent<HTMLDivElement>) => {
		const { scrollTop, clientHeight, scrollHeight } = e.currentTarget;
		if (scrollTop + clientHeight >= scrollHeight - 10 && !authoritiesLoading) {
			setCurrentAuthoritiesPage((prev) => prev + 1);
			loadAuthorities();
		}
	};

	useEffect(() => {
		loadAuthorities();
	}, []);

	return (
		<div style={{ padding: '20px' }}>
			<h1>Управление ролями</h1>

			<Form form={form} onFinish={onFinish} layout="vertical" style={{ marginBottom: '20px' }}>
				<Form.Item
					name="name"
					label="Название роли"
					rules={[{ required: true, message: 'Пожалуйста, введите название роли!' }]}
				>
					<Input />
				</Form.Item>
				<Form.Item
					name="description"
					label="Описание роли"
					rules={[{ required: true, message: 'Пожалуйста, введите описание роли!' }]}
				>
					<Input.TextArea />
				</Form.Item>
				<Form.Item
					name="authorityIds"
					label="Функциональные возможности"
					rules={[{ required: true, message: 'Пожалуйста, выберите функциональные возможности!' }]}
				>
					<div style={{ maxHeight: '200px', overflowY: 'auto' }} onScroll={handleScroll}>
						<Select
							mode="multiple"
							placeholder="Выберите authorities"
							style={{ width: '100%' }}
							onChange={(value) => form.setFieldsValue({ authorityIds: value })}
						>
							{authoritiesLoading ? (
								<Spin />
							) : (
								authorities.map((authority) => (
									<Option key={authority.id} value={authority.id}>
										{authority.name} - {authority.description}
									</Option>
								))
							)}
						</Select>
					</div>
				</Form.Item>
				<Form.Item>
					<Button type="primary" htmlType="submit">
						{editingRoleId ? 'Обновить' : 'Создать'}
					</Button>
				</Form.Item>
			</Form>

			<h2 style={{ marginTop: '20px' }}>Список ролей</h2>
			<List
				loading={loading}
				bordered
				dataSource={roles}
				renderItem={(role) => (
					<List.Item
						actions={[
							<Button onClick={() => handleEdit(role)} type="link">
								Редактировать
							</Button>,
							<Button onClick={() => handleDelete(role.id!)} type="link" danger>
								Удалить
							</Button>,
						]}
					>
						<div>
							<strong>{role.name}</strong>: {role.description}
							<div style={{ marginTop: '10px' }}>
								<strong>Функциональные возможности:</strong>
								<ul>
									{role.authorities?.map((authority) => (
										<li key={authority.id}>
											{authority.name} - {authority.description}
										</li>
									))}
								</ul>
							</div>
						</div>
					</List.Item>
				)}
			/>

			<Pagination
				current={currentPage + 1}
				pageSize={10}
				total={totalRoles}
				onChange={(page) => setCurrentPage(page - 1)}
				style={{ marginTop: '20px', textAlign: 'right' }}
			/>
		</div>
	);
};

export default RolesPage;
