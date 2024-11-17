import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, message } from 'antd';
import { ColumnsType } from 'antd/es/table';
import { StarTwoTone } from '@ant-design/icons';
import { API_URL } from '../config/constants';
import { config } from '../config/request-config';
import { Currency } from '../types/Currency';

interface FavoriteMap {
	[id: string]: boolean;
}

const CurrenciesPage: React.FC = () => {
	const [cryptos, setCryptos] = useState<Currency[]>([]);
	const [favoriteMap, setFavoriteMap] = useState<FavoriteMap>({});

	useEffect(() => {
		const fetchCryptos = async () => {
			try {
				const response = await axios.get(`${API_URL}/external-api-service/currencies`, config);
				console.log(response);
				setCryptos(response.data);
				const ids = response.data.map((crypto: Currency) => crypto.id);
				fetchFavorites(ids);
			} catch (error) {
				console.error('Ошибка при загрузке данных о криптовалютах:', error);
				message.error('Не удалось загрузить данные о криптовалютах.');
			}
		};

		const fetchFavorites = async (ids: number[]) => {
			try {
				const idsParam = ids.join(',');
				const response = await axios.get(
					`${API_URL}/external-api-service/currencies/is-in-favorite-by-ids?ids=${idsParam}`,
					config
				);
				setFavoriteMap(response.data);
			} catch (error) {
				console.error('Ошибка при загрузке избранных криптовалют:', error);
				message.error('Не удалось загрузить избранные криптовалюты.');
			}
		};

		fetchCryptos();
	}, []);

	const toggleFavorite = async (currencyId: string) => {
		try {
			await axios.patch(
				`${API_URL}/external-api-service/currencies/favorites/${currencyId}`,
				{},
				config
			);
			setFavoriteMap((prev) => ({
				...prev,
				[currencyId]: !prev[currencyId],
			}));
			message.success(
				favoriteMap[currencyId]
					? 'Криптовалюта удалена из избранного.'
					: 'Криптовалюта добавлена в избранное.'
			);
		} catch (error) {
			console.error('Ошибка при изменении избранного:', error);
			message.error('Не удалось изменить статус криптовалюты в избранном.');
		}
	};

	const columns: ColumnsType<Currency> = [
		{
			title: 'Rank',
			dataIndex: 'rank',
			key: 'rank',
		},
		{
			title: 'Symbol',
			dataIndex: 'symbol',
			key: 'symbol',
		},
		{
			title: 'Name',
			dataIndex: 'name',
			key: 'name',
		},
		{
			title: 'Supply',
			dataIndex: 'supply',
			key: 'supply',
			render: (text) => text.toLocaleString(),
		},
		{
			title: 'Max Supply',
			dataIndex: 'maxSupply',
			key: 'maxSupply',
			render: (text) => (text ? text.toLocaleString() : 'N/A'),
		},
		{
			title: 'Market Cap (USD)',
			dataIndex: 'marketCapUsd',
			key: 'marketCapUsd',
			render: (text) => text.toLocaleString(),
		},
		{
			title: 'Volume (24h)',
			dataIndex: 'volumeUsd24Hr',
			key: 'volumeUsd24Hr',
			render: (text) => text.toLocaleString(),
		},
		{
			title: 'Price (USD)',
			dataIndex: 'priceUsd',
			key: 'priceUsd',
			render: (text) => `$${text.toFixed(2)}`,
		},
		{
			title: 'Change (24h)',
			dataIndex: 'changePercent24Hr',
			key: 'changePercent24Hr',
			render: (text) => `${text.toFixed(2)}%`,
		},
		{
			title: 'VWAP (24h)',
			dataIndex: 'vwap24Hr',
			key: 'vwap24Hr',
			render: (text) => `$${text.toFixed(2)}`,
		},
		{
			title: 'Favorites',
			key: 'favorites',
			render: (text, record) => (
				<span onClick={() => toggleFavorite(record.id)} style={{ cursor: 'pointer' }}>
					{favoriteMap[record.id] ? (
						<StarTwoTone twoToneColor="gold" />
					) : (
						<StarTwoTone twoToneColor="black" />
					)}
				</span>
			),
		},
	];

	return (
		<div style={{ padding: '20px' }}>
			<h1>Данные о криптовалютах</h1>
			<Table columns={columns} dataSource={cryptos} rowKey="id" />
		</div>
	);
};

export default CurrenciesPage;
