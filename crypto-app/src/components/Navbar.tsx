import React, { useEffect, useState } from 'react';
import { Menu } from 'antd';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { API_URL } from '../config/constants';
import { config } from '../config/request-config';
import { Authority } from '../types/Authority';

const Navbar: React.FC = () => {
	const [authorities, setAuthorities] = useState<Authority[]>([]);

	useEffect(() => {
		const fetchAuthorities = async () => {
			try {
				const response = await axios.get(
					`${API_URL}/auth-service/authorities/my-authorities`,
					config
				);
				setAuthorities(response.data);
			} catch (error) {
				console.error('Ошибка при получении прав пользователя:', error);
			}
		};

		fetchAuthorities();
	}, []);

	return (
		<Menu mode="horizontal">
			<Menu.Item key="home">
				<Link to="/">Главная</Link>
			</Menu.Item>
			<Menu.Item key="create-report">
				<Link to="/create-report">Создать отчет</Link>
			</Menu.Item>
			{authorities.find((a) => a.name === 'MANAGE_CURRENCIES') && (
				<Menu.Item key="currencies">
					<Link to="/currencies">Криптовалюты</Link>
				</Menu.Item>
			)}
			{authorities.find((a) => a.name === 'MANAGE_CURRENCIES') && (
				<Menu.Item key="crypto-subscriptions">
					<Link to="/crypto-subscriptions">Подписки на цены</Link>
				</Menu.Item>
			)}
			<Menu.Item key="profile">
				<Link to="/profile">Профиль</Link>
			</Menu.Item>
			{authorities.find((a) => a.name === 'MANAGE_ROLES') && (
				<Menu.Item key="roles">
					<Link to="/roles">Управление ролями</Link>
				</Menu.Item>
			)}
			{authorities.find((a) => a.name === 'MANAGE_USERS') &&
				authorities.find((a) => a.name === 'MANAGE_ROLES') && (
					<Menu.Item key="reassign-roles">
						<Link to="/reassign-roles">Назначение ролей</Link>
					</Menu.Item>
				)}
		</Menu>
	);
};

export default Navbar;
