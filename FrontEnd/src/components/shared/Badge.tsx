import './Badge.css';

type Variant = 'success' | 'danger' | 'warning' | 'info' | 'neutral';

interface Props {
  label: string;
  variant: Variant;
}

export const Badge = ({ label, variant }: Props) => (
  <span className={`badge badge--${variant}`}>{label}</span>
);
