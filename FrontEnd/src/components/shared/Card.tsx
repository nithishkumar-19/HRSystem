import './Card.css';

interface Props {
  title?: string;
  children: React.ReactNode;
  flush?: boolean;
  className?: string;
}

export const Card = ({ title, children, flush = false, className = '' }: Props) => (
  <div className={`card ${flush ? 'card--flush' : ''} ${className}`.trim()}>
    {title && (
      <div className="card__header">
        <h3 className="card__title">{title}</h3>
      </div>
    )}
    <div className="card__body">{children}</div>
  </div>
);
