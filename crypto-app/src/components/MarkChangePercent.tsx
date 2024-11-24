import { CaretDownOutlined, CaretUpOutlined } from '@ant-design/icons';

interface Props {
	value: number;
}

const MarkChangePercent = ({ value }: Props) => {
	if (value < 0)
		return (
			<div style={{ display: 'flex', gap: '0.25rem', color: 'red' }}>
				<CaretDownOutlined />
				{Math.abs(value)}%
			</div>
		);
	if (value > 0)
		return (
			<div style={{ display: 'flex', gap: '0.25rem', color: 'green' }}>
				<CaretUpOutlined />
				{value}%
			</div>
		);
	return <div>{value}%</div>;
};

export default MarkChangePercent;
