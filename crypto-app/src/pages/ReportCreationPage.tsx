import React, { useState } from 'react';
import { Form, Input, Button, Upload, message, Switch } from 'antd';
import { UploadOutlined } from '@ant-design/icons';
import axios from 'axios';
import { API_URL } from '../config/constants';
import { config } from '../config/request-config';
import { FileMetadata } from '../types/FileMetadata';

const ReportCreationPage: React.FC = () => {
	const [form] = Form.useForm();
	const [sourceFileId, setSourceFileId] = useState<string | null>(null);
	const [isPublic, setIsPublic] = useState<boolean>(false);

	const handleUpload = async (file: File): Promise<void> => {
		const formData = new FormData();
		formData.append('file', file);

		try {
			const response = await axios.post<FileMetadata>(`${API_URL}/file-service/files`, formData, {
				headers: {
					'Content-Type': 'multipart/form-data',
				},
			});
			setSourceFileId(response.data.id);
			message.success('Файл загружен успешно!');
		} catch (error) {
			message.error('Ошибка загрузки файла.');
		}
	};

	const onFinish = async (values: { title: string; description: string }) => {
		const reportData = {
			title: values.title,
			description: values.description,
			sourceFileId: sourceFileId,
			isPublic: isPublic,
		};

		try {
			await axios.post(
				`${API_URL}/data-analyze-service/reports/generate-report`,
				reportData,
				config
			);
			message.success('Отчет успешно создан!');
			form.resetFields();
			setSourceFileId(null);
		} catch (error) {
			message.error('Ошибка создания отчета.');
		}
	};

	return (
		<div style={{ maxWidth: 400, margin: 'auto' }}>
			<h2>Создание отчета</h2>
			<Form form={form} onFinish={onFinish}>
				<Form.Item name="title" rules={[{ required: true, message: 'Введите название отчета!' }]}>
					<Input placeholder="Название отчета" />
				</Form.Item>
				<Form.Item
					name="description"
					rules={[{ required: true, message: 'Введите описание отчета!' }]}
				>
					<Input.TextArea placeholder="Описание отчета" rows={4} />
				</Form.Item>
				<Form.Item>
					<Upload
						beforeUpload={(file) => {
							handleUpload(file);
							return false;
						}}
					>
						<Button icon={<UploadOutlined />}>Загрузить файл</Button>
					</Upload>
				</Form.Item>
				<Form.Item label="Публичный">
					<Switch checked={isPublic} onChange={setIsPublic} />
				</Form.Item>
				<Form.Item>
					<Button type="primary" htmlType="submit">
						Создать отчет
					</Button>
				</Form.Item>
			</Form>
		</div>
	);
};

export default ReportCreationPage;
