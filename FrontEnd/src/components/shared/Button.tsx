import './Button.css';

type Variant = 'primary' | 'success' | 'danger' | 'warning' | 'ghost';
type Size    = 'sm' | 'md' | 'lg';

interface Props extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  variant?: Variant;
  size?: Size;
  loading?: boolean;
}

export const Button = ({
  variant = 'primary',
  size = 'md',
  loading = false,
  children,
  className = '',
  disabled,
  ...rest
}: Props) => (
  <button
    className={`btn btn--${variant} ${size !== 'md' ? `btn--${size}` : ''} ${className}`.trim()}
    disabled={disabled || loading}
    {...rest}
  >
    {loading ? 'Loading…' : children}
  </button>
);
