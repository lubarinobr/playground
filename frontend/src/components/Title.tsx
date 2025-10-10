import React from 'react';

interface TitleProps {
  children: React.ReactNode;
  level?: 'h1' | 'h2' | 'h3' | 'h4' | 'h5' | 'h6';
  variant?: 'default' | 'gradient' | 'muted';
  className?: string;
}

const Title: React.FC<TitleProps> = ({
  children,
  level = 'h1',
  variant = 'default',
  className = '',
}) => {
  const baseClasses = 'font-bold tracking-tight';
  
  const levelClasses = {
    h1: 'text-4xl md:text-5xl',
    h2: 'text-3xl md:text-4xl',
    h3: 'text-2xl md:text-3xl',
    h4: 'text-xl md:text-2xl',
    h5: 'text-lg md:text-xl',
    h6: 'text-base md:text-lg'
  };
  
  const variantClasses = {
    default: 'text-gray-900',
    gradient: 'bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent',
    muted: 'text-gray-600'
  };
  
  const classes = `${baseClasses} ${levelClasses[level]} ${variantClasses[variant]} ${className}`;
  
  const Component = level;
  
  return <Component className={classes}>{children}</Component>;
};

export default Title; 