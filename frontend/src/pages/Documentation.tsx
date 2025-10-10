import React, { useState, useEffect } from 'react';
import Title from '../components/Title';
import Button from '../components/Button';
import Card from '../components/Card';
import apiService from '../services/api';

interface Post {
  id: number;
  title: string;
  body: string;
  userId: number;
}

const Documentation: React.FC = () => {
  const [posts, setPosts] = useState<Post[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const fetchPosts = async () => {
    setLoading(true);
    setError(null);
    try {
      const response = await apiService.get<Post[]>('/posts?_limit=5');
      setPosts(response.data);
    } catch (err: any) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100">
      <div className="max-w-7xl mx-auto px-6 sm:px-8 lg:px-12 py-16">
        <div className="text-center mb-20">
          <Title level="h1" variant="gradient" className="mb-8 text-5xl lg:text-6xl">
            Documentation Center
          </Title>
          <p className="text-xl lg:text-2xl text-gray-600 max-w-4xl mx-auto leading-relaxed">
            Everything you need to integrate and build with our platform. 
            Comprehensive guides, API references, and real-world examples.
          </p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8 lg:gap-12 mb-20">
          <Card variant="elevated" className="text-center group p-8">
            <div className="w-16 h-16 bg-gradient-to-r from-blue-500 to-blue-600 rounded-2xl flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-200">
              <svg className="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.746 0 3.332.477 4.5 1.253v13C19.832 18.477 18.246 18 16.5 18c-1.746 0-3.332.477-4.5 1.253" />
              </svg>
            </div>
            <Title level="h3" className="mb-4 text-xl">Getting Started</Title>
            <p className="text-gray-600 mb-8 leading-relaxed text-lg">
              Learn the basics and set up your development environment with our step-by-step guides.
            </p>
            <Button variant="primary" size="lg">
              Read Guide
            </Button>
          </Card>

          <Card variant="elevated" className="text-center group p-8">
            <div className="w-16 h-16 bg-gradient-to-r from-purple-500 to-purple-600 rounded-2xl flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-200">
              <svg className="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4" />
              </svg>
            </div>
            <Title level="h3" className="mb-4 text-xl">API Reference</Title>
            <p className="text-gray-600 mb-8 leading-relaxed text-lg">
              Complete API documentation with detailed examples and endpoint specifications.
            </p>
            <Button variant="outline" size="lg">
              View API
            </Button>
          </Card>

          <Card variant="elevated" className="text-center group p-8">
            <div className="w-16 h-16 bg-gradient-to-r from-green-500 to-green-600 rounded-2xl flex items-center justify-center mx-auto mb-6 group-hover:scale-110 transition-transform duration-200">
              <svg className="w-8 h-8 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z" />
              </svg>
            </div>
            <Title level="h3" className="mb-4 text-xl">Examples</Title>
            <p className="text-gray-600 mb-8 leading-relaxed text-lg">
              Code examples and tutorials for common use cases and integration patterns.
            </p>
            <Button variant="outline" size="lg">
              Browse Examples
            </Button>
          </Card>
        </div>

        <Card className="mb-16 p-8">
          <div className="flex flex-col lg:flex-row lg:items-center justify-between mb-10">
            <div className="mb-6 lg:mb-0">
              <Title level="h2" className="mb-4 text-3xl">API Data Example</Title>
              <p className="text-gray-600 text-lg">Real-time data from our demo API endpoint</p>
            </div>
            <Button 
              onClick={fetchPosts} 
              disabled={loading}
              variant="primary"
              size="lg"
            >
              {loading ? (
                <div className="flex items-center space-x-3">
                  <div className="w-5 h-5 border-2 border-white border-t-transparent rounded-full animate-spin"></div>
                  <span>Loading...</span>
                </div>
              ) : (
                'Fetch Posts'
              )}
            </Button>
          </div>
          
          {error && (
            <div className="mb-8 p-6 bg-red-50 border border-red-200 rounded-xl">
              <p className="text-red-600 text-lg">{error}</p>
            </div>
          )}
          
          {posts.length > 0 && (
            <div className="space-y-6">
              {posts.map((post) => (
                <div key={post.id} className="border border-gray-200 rounded-2xl p-8 hover:border-gray-300 transition-colors duration-200">
                  <div className="flex items-start justify-between">
                    <div className="flex-1 pr-8">
                      <Title level="h4" className="mb-4 text-xl text-gray-900">{post.title}</Title>
                      <p className="text-gray-600 leading-relaxed mb-4 text-lg">{post.body}</p>
                      <div className="flex items-center space-x-6 text-sm text-gray-500">
                        <span className="flex items-center space-x-2">
                          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                          </svg>
                          <span>User ID: {post.userId}</span>
                        </span>
                        <span className="flex items-center space-x-2">
                          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                          </svg>
                          <span>Post ID: {post.id}</span>
                        </span>
                      </div>
                    </div>
                    <div className="flex-shrink-0">
                      <div className="w-16 h-16 bg-blue-100 rounded-2xl flex items-center justify-center">
                        <span className="text-blue-600 text-xl font-bold">{post.userId}</span>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </Card>

        <Card className="p-8">
          <Title level="h2" className="mb-8 text-center text-3xl">Quick Actions</Title>
          <div className="flex flex-col sm:flex-row flex-wrap justify-center gap-6">
            <Button variant="primary" size="lg" className="min-w-[200px]">
              <div className="flex items-center space-x-3">
                <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 10v6m0 0l-3-3m3 3l3-3m2 8H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                </svg>
                <span>Download SDK</span>
              </div>
            </Button>
            <Button variant="secondary" size="lg" className="min-w-[200px]">
              <div className="flex items-center space-x-3">
                <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                </svg>
                <span>View Changelog</span>
              </div>
            </Button>
            <Button variant="ghost" size="lg" className="min-w-[200px]">
              <div className="flex items-center space-x-3">
                <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M18.364 5.636l-3.536 3.536m0 5.656l3.536 3.536M9.172 9.172L5.636 5.636m3.536 9.192L5.636 18.364M12 2.25a9.75 9.75 0 109.75 9.75A9.75 9.75 0 0012 2.25z" />
                </svg>
                <span>Support</span>
              </div>
            </Button>
          </div>
        </Card>
      </div>
    </div>
  );
};

export default Documentation; 