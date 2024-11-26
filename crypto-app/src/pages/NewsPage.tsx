import React, { useEffect, useState } from 'react';
import { Card, Button, Col, Row, Typography, Spin } from 'antd';
import axios from 'axios';
import { News } from '../types/News';
import { config } from '../config/request-config';
import { API_URL } from '../config/constants';
import '../styles/NewsPage.css';

const { Title, Paragraph } = Typography;

const NewsPage: React.FC = () => {
	const [news, setNews] = useState<News[]>([]);
	const [loading, setLoading] = useState<boolean>(true);
	const [newsType, setNewsType] = useState<'all' | 'favorites'>('all');

	const fetchNews = async () => {
		try {
			setLoading(true);
			let endpoint = '';
			switch (newsType) {
				case 'favorites':
					endpoint = `${API_URL}/external-api-service/news/about-favorite-currencies`;
					break;
				default:
					endpoint = `${API_URL}/external-api-service/news`;
			}

			const response = await axios.get<News[]>(endpoint, config);
			setNews(response.data);
		} catch (error) {
			console.error('Ошибка при загрузке новостей:', error);
		} finally {
			setLoading(false);
		}
	};

	useEffect(() => {
		fetchNews();
	}, []);

	const handleNewsTypeChange = (type: 'all' | 'favorites') => {
		setNewsType(type);
		fetchNews();
	};

	if (loading) {
		return <Spin size="large" />;
	}

	const formatDate = (dateString: string): string => {
		const year = dateString.substring(0, 4);
		const month = dateString.substring(4, 6);
		const day = dateString.substring(6, 8);
		const hours = dateString.substring(9, 11);
		const minutes = dateString.substring(11, 13);

		const date = new Date(`${year}-${month}-${day}T${hours}:${minutes}:00`);

		return date.toLocaleString('ru-RU', {
			year: 'numeric',
			month: 'long',
			day: 'numeric',
			hour: '2-digit',
			minute: '2-digit',
			hour12: false,
		});
	};

	return (
		<div style={{ padding: '20px' }}>
			<h2>Новости</h2>
			<div style={{ marginBottom: 16 }}>
				<Button
					type={newsType === 'all' ? 'primary' : 'default'}
					onClick={() => handleNewsTypeChange('all')}
					style={{ marginRight: 8 }}
				>
					Все новости
				</Button>
				<Button
					type={newsType === 'favorites' ? 'primary' : 'default'}
					onClick={() => handleNewsTypeChange('favorites')}
				>
					Новости об избранных криптовалютах
				</Button>
			</div>
			<Row gutter={16} justify="start">
				{news.map((item, index) => (
					<Col span={8} key={index} style={{ display: 'flex', paddingBottom: '16px' }}>
						<Card
							hoverable
							style={{ display: 'flex', flex: 1, flexDirection: 'column' }}
							cover={
								item.banner_image ? (
									<img
										alt={item.title}
										src={item.banner_image}
										style={{ objectFit: 'cover', height: 300 }}
									/>
								) : (
									<div style={{ height: 300, backgroundColor: '#D9D9D9' }}></div>
								)
							}
							actions={[
								<a
									href={item.url}
									target="_blank"
									rel="noopener noreferrer"
									style={{ color: '#1677ff' }}
								>
									Читать далее
								</a>,
							]}
						>
							<div style={{ flex: 1 }}>
								<Title level={4}>{item.title}</Title>
								<Paragraph>{item.summary}</Paragraph>
								<Paragraph type="secondary">
									Опубликовано: {formatDate(item.time_published)}
								</Paragraph>
								<Paragraph type="secondary">Источник: {item.source}</Paragraph>
							</div>
						</Card>
					</Col>
				))}
			</Row>
		</div>
	);
};

export default NewsPage;
