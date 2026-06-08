import './FormField.css';

interface BaseProps {
  label: string;
  id: string;
}

interface InputProps extends BaseProps, React.InputHTMLAttributes<HTMLInputElement> {
  as?: 'input';
}

interface SelectProps extends BaseProps, React.SelectHTMLAttributes<HTMLSelectElement> {
  as: 'select';
  children: React.ReactNode;
}

type Props = InputProps | SelectProps;

export const FormField = (props: Props) => {
  const { label, id, as = 'input', ...rest } = props;

  return (
    <div className="field">
      <label className="field__label" htmlFor={id}>{label}</label>
      {as === 'select' ? (
        <select
          id={id}
          className="field__select"
          {...(rest as React.SelectHTMLAttributes<HTMLSelectElement>)}
        >
          {(props as SelectProps).children}
        </select>
      ) : (
        <input
          id={id}
          className="field__input"
          {...(rest as React.InputHTMLAttributes<HTMLInputElement>)}
        />
      )}
    </div>
  );
};
