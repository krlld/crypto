import React, { useEffect, useState } from 'react';
import { Table, Button, Select, message, Pagination } from 'antd';
import axios from 'axios';
import { User } from '../types/User';
import { Role } from '../types/Role';
import { Authority } from '../types/Authority';
import { API_URL } from '../config/constants';
import { config } from '../config/request-config';
import { Page } from '../types/Page';

const { Option } = Select;

const RoleAssignmentPage: React.FC = () => {
	const [users, setUsers] = useState<User[]>([]);
	const [roles, setRoles] = useState<Role[]>([]);
	const [loadingUsers, setLoadingUsers] = useState<boolean>(true);
	const [loadingRoles, setLoadingRoles] = useState<boolean>(true);
	const [currentUserPage, setCurrentUserPage] = useState<number>(0);
	const [currentRolePage, setCurrentRolePage] = useState<number>(0);
	const [totalUsers, setTotalUsers] = useState<number>(0);
	const [totalRoles, setTotalRoles] = useState<number>(0);

	useEffect(() => {
		const fetchUsers = async () => {
			setLoadingUsers(true);
			try {
				const response = await axios.get<Page<User>>(
					`${API_URL}/auth-service/users?page=${currentUserPage}`,
					config
				);
				const usersWithRoles = response.data.content.map((user) => ({
					...user,
					roleIds: user.roles
						? user.roles.map((role) => (role.id === undefined ? 0 : role.id))
						: [],
				}));
				setUsers(usersWithRoles);
				setTotalUsers(response.data.page.totalElements);
			} catch (error) {
				message.error('Ошибка при загрузке пользователей');
			} finally {
				setLoadingUsers(false);
			}
		};

		fetchUsers();
	}, [currentUserPage]);

	useEffect(() => {
		const fetchRoles = async () => {
			setLoadingRoles(true);
			try {
				const response = await axios.get<Page<Role>>(
					`${API_URL}/auth-service/roles?page=${currentRolePage}`,
					config
				);
				setRoles(response.data.content);
				setTotalRoles(response.data.page.totalElements);
			} catch (error) {
				message.error('Ошибка при загрузке ролей');
			} finally {
				setLoadingRoles(false);
			}
		};

		fetchRoles();
	}, [currentRolePage]);

	const handleRoleChange = (userId: number | undefined, selectedRoles: number[]) => {
		setUsers((prevUsers) =>
			prevUsers.map((user) => (user.id === userId ? { ...user, roleIds: selectedRoles } : user))
		);
	};

	const handleSaveRoles = async () => {
		try {
			await Promise.all(
				users.map((user) => {
					if (user.roleIds) {
						return axios.patch(
							`${API_URL}/auth-service/users/reassign-roles`,
							{ userId: user.id, newRoleIds: user.roleIds },
							config
						);
					}
				})
			);
			message.success('Роли успешно обновлены!');
		} catch (error) {
			message.error('Ошибка при обновлении ролей');
		}
	};

	const renderAuthorities = (authorities: Authority[]) => {
		return (
			<ul style={{ padding: 0, margin: 0 }}>
				{authorities.map((auth) => (
					<li key={auth.id} style={{ listStyle: 'none' }}>
						{auth.name} - {auth.description}
					</li>
				))}
			</ul>
		);
	};

	const columns = [
		{
			title: 'Email',
			dataIndex: 'email',
		},
		{
			title: 'Имя',
			dataIndex: 'name',
		},
		{
			title: 'Фамилия',
			dataIndex: 'lastname',
		},
		{
			title: 'Роли',
			dataIndex: 'roleIds',
			render: (roleIds: number[], record: User) => (
				<Select
					mode="multiple"
					style={{ width: '100%' }}
					value={roleIds}
					onChange={(value) => handleRoleChange(record.id, value)}
				>
					{roles.map((role) => (
						<Option key={role.id} value={role.id}>
							{role.name}
						</Option>
					))}
				</Select>
			),
		},
	];

	const roleColumns = [
		{
			title: 'Название роли',
			dataIndex: 'name',
		},
		{
			title: 'Описание',
			dataIndex: 'description',
		},
		{
			title: 'Функциональные возможности',
			dataIndex: 'authorities',
			render: (authorities: Authority[]) => renderAuthorities(authorities),
		},
	];

	return (
		<div style={{ padding: '20px' }}>
			<h1>Назначение ролей пользователям</h1>
			<Table
				loading={loadingUsers}
				dataSource={users}
				columns={columns}
				rowKey="id"
				pagination={false}
			/>
			<Pagination
				current={currentUserPage + 1}
				pageSize={10}
				total={totalUsers}
				onChange={(page) => setCurrentUserPage(page - 1)}
				style={{ marginTop: '20px', textAlign: 'right' }}
			/>
			<Button type="primary" onClick={handleSaveRoles} style={{ marginTop: '20px' }}>
				Сохранить изменения
			</Button>

			<h2 style={{ marginTop: '40px' }}>Доступные роли</h2>
			<Table
				loading={loadingRoles}
				dataSource={roles}
				columns={roleColumns}
				rowKey="id"
				pagination={false}
			/>
			<Pagination
				current={currentRolePage + 1}
				pageSize={10}
				total={totalRoles}
				onChange={(page) => setCurrentRolePage(page - 1)}
				style={{ marginTop: '20px', textAlign: 'right' }}
			/>
		</div>
	);
};

export default RoleAssignmentPage;
