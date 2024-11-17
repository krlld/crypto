import React, { useEffect, useState } from 'react';
import { Form, Select, Input, Button, message, List, Pagination, Card } from 'antd';
import axios from 'axios';
import { Page } from '../types/Page';
import { Currency } from '../types/Currency';
import { API_URL } from '../config/constants';
import { config } from '../config/request-config';
import { SubscriptionToPrice } from '../types/SubscriptionToPrice';
import { ComparisonType } from '../types/ComparisonType';

const CryptoSubscriptionPage: React.FC = () => {
	const [currencies, setCurrencies] = useState<Currency[]>([]);
	const [subscriptions, setSubscriptions] = useState<SubscriptionToPrice[]>([]);
	const [loadingCurrencies, setLoadingCurrencies] = useState<boolean>(true);
	const [loadingSubscriptions, setLoadingSubscriptions] = useState<boolean>(true);
	const [form] = Form.useForm();
	const [currentPage, setCurrentPage] = useState<number>(0);
	const [totalSubscriptions, setTotalSubscriptions] = useState<number>(0);
	const [pageSize, setPageSize] = useState<number>(10);

	useEffect(() => {
		const fetchCurrencies = async () => {
			try {
				const response = await axios.get<Currency[]>(
					`${API_URL}/external-api-service/currencies`,
					config
				);
				setCurrencies(response.data);
			} catch (error) {
				message.error('Ошибка при загрузке данных криптовалют');
			} finally {
				setLoadingCurrencies(false);
			}
		};

		fetchCurrencies();
	}, []);

	const fetchSubscriptions = async () => {
		setLoadingSubscriptions(true);
		try {
			const response = await axios.get<Page<SubscriptionToPrice>>(
				`${API_URL}/external-api-service/currencies/subscription-to-prices?page=${
					currentPage - 1
				}&size=${pageSize}`,
				config
			);
			setSubscriptions(response.data.content);
			setTotalSubscriptions(response.data.page.totalElements);
		} catch (error) {
			message.error('Ошибка при загрузке подписок');
			console.log(error);
		} finally {
			setLoadingSubscriptions(false);
		}
	};

	useEffect(() => {
		fetchSubscriptions();
	}, [currentPage, pageSize]);

	const onFinish = async (values: any) => {
		try {
			await axios.post(
				`${API_URL}/external-api-service/currencies/subscribe-to-price`,
				values,
				config
			);
			message.success('Подписка успешно создана!');
			form.resetFields();
			await fetchSubscriptions();
		} catch (error) {
			message.error('Ошибка при подписке');
		}
	};

	const handlePageChange = (page: number) => {
		setCurrentPage(page);
	};

	const handlePageSizeChange = (current: number, size: number) => {
		setPageSize(size);
		setCurrentPage(0);
	};

	return (
		<div style={{ padding: '20px' }}>
			<h1>Подписка на изменения цены криптовалют</h1>
			<Form form={form} onFinish={onFinish} layout="vertical">
				<Form.Item name="currencyId" label="Выберите валюту" rules={[{ required: true }]}>
					<Select loading={loadingCurrencies} placeholder="Выберите валюту">
						{currencies.map((currency) => (
							<Select.Option key={currency.id} value={currency.id}>
								{currency.name} ({currency.symbol})
							</Select.Option>
						))}
					</Select>
				</Form.Item>

				<Form.Item name="comparisonType" label="Тип сравнения" rules={[{ required: true }]}>
					<Select placeholder="Выберите тип сравнения">
						<Select.Option value={ComparisonType.GRATER_THAN}>Больше чем</Select.Option>
						<Select.Option value={ComparisonType.LESS_THAN}>Меньше чем</Select.Option>
					</Select>
				</Form.Item>

				<Form.Item name="price" label="Цена" rules={[{ required: true }]}>
					<Input type="number" step="0.01" />
				</Form.Item>

				<Form.Item>
					<Button type="primary" htmlType="submit">
						Подписаться
					</Button>
				</Form.Item>
			</Form>

			<List
				loading={loadingSubscriptions}
				header={<div>Ваши подписки</div>}
				bordered
				dataSource={subscriptions}
				renderItem={(item) => (
					<List.Item>
						<Card style={{ width: '100%' }}>
							<Card.Meta
								title={`Валюта: ${item.currencyId}`}
								description={`Условие уведомления: ${
									item.comparisonType === ComparisonType.GRATER_THAN
										? `Цена станет больше чем ${item.price} USD`
										: `Цена станет меньше чем ${item.price} USD`
								}`}
							/>
						</Card>
					</List.Item>
				)}
			/>
			<Pagination
				current={currentPage + 1}
				pageSize={pageSize}
				total={totalSubscriptions}
				onChange={handlePageChange}
				onShowSizeChange={handlePageSizeChange}
				showSizeChanger
				style={{ marginTop: '20px', textAlign: 'center' }}
			/>
		</div>
	);
};

export default CryptoSubscriptionPage;
