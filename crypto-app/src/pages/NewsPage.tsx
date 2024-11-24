import React, { useEffect, useState } from 'react';
import { Card, Col, Row, Typography, Spin } from 'antd';
import axios from 'axios';
import { News } from '../types/News';
import { config } from '../config/request-config';
import { API_URL } from '../config/constants';

const { Title, Paragraph } = Typography;

const NewsPage: React.FC = () => {
	const [news, setNews] = useState<News[]>([]);
	const [loading, setLoading] = useState<boolean>(true);

	useEffect(() => {
		const fetchNews = async () => {
			try {
				const response = await axios.get<News[]>(`${API_URL}/external-api-service/news`, config);
				setNews(response.data);
			} catch (error) {
				console.error('Ошибка при загрузке новостей:', error);
			} finally {
				setLoading(false);
			}
		};

		fetchNews();
	}, []);

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
			<Title level={2}>Новости</Title>
			<Row gutter={16}>
				{news.map((item, index) => (
					<Col span={8} key={index} style={{ marginBottom: '20px' }}>
						<Card
							hoverable
							cover={item.banner_image ? <img alt={item.title} src={item.banner_image} /> : null}
							actions={[
								<a href={item.url} target="_blank" rel="noopener noreferrer">
									Читать далее
								</a>,
							]}
						>
							<Title level={4}>{item.title}</Title>
							<Paragraph>{item.summary}</Paragraph>
							<Paragraph type="secondary">
								Опубликовано: {formatDate(item.time_published)}
							</Paragraph>
							<Paragraph type="secondary">Источник: {item.source}</Paragraph>
						</Card>
					</Col>
				))}
			</Row>
		</div>
	);
};

export default NewsPage;
