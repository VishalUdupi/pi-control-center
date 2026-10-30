
import './App.css'

const metrics = [
  { label: 'CPU Usage', value: '—', unit: '%', detail: 'Waiting for data', icon: '◫', tone: 'blue' },
  { label: 'Memory Usage', value: '—', unit: '%', detail: 'Waiting for data', icon: '▤', tone: 'purple' },
  { label: 'CPU Temperature', value: '—', unit: '°C', detail: 'Waiting for data', icon: '♨', tone: 'orange' },
  { label: 'System Uptime', value: '—', unit: '', detail: 'Waiting for data', icon: '◷', tone: 'green' },
]

const services = [
  { name: 'Spring Boot API', description: 'System metrics service', status: 'Not connected' },
  { name: 'Redis', description: 'Current metrics store', status: 'Not connected' },
  { name: 'PostgreSQL', description: 'Historical metrics store', status: 'Not connected' },
]

function App() {
  return (
      <div className="app-shell">
        <aside className="sidebar">
          <a className="brand" href="#" aria-label="Pi Control Center home">
            <span className="brand-mark">π</span>
            <span>Pi Control<span className="brand-muted"> Center</span></span>
          </a>

          <div className="nav-label">WORKSPACE</div>
          <nav className="navigation" aria-label="Main navigation">
            <a className="nav-item active" href="#overview"><span>▦</span> Overview</a>
            <a className="nav-item" href="#performance"><span>⌁</span> Performance</a>
            <a className="nav-item" href="#network"><span>⌘</span> Network</a>
            <a className="nav-item" href="#storage"><span>▤</span> Storage</a>
          </nav>

          <div className="nav-label infrastructure-label">INFRASTRUCTURE</div>
          <nav className="navigation" aria-label="Infrastructure navigation">
            <a className="nav-item" href="#services"><span>◈</span> Services</a>
            <a className="nav-item" href="#history"><span>◷</span> History</a>
          </nav>

          <div className="sidebar-bottom">
            <div className="pi-avatar">π</div>
            <div className="sidebar-device">
              <strong>Raspberry Pi</strong>
              <span>Control node</span>
            </div>
            <span className="more-icon">···</span>
          </div>
        </aside>

        <main className="main-content" id="overview">
          <header className="topbar">
            <div className="breadcrumb">Workspace <span>/</span> <strong>Overview</strong></div>
            <div className="topbar-right">
              <span className="environment"><span className="environment-dot" /> Local environment</span>
              <button className="icon-button" type="button" aria-label="Notifications">♧</button>
              <div className="user-avatar">V</div>
            </div>
          </header>

          <div className="page-content">
            <section className="page-heading">
              <div>
                <div className="eyebrow">SYSTEM MONITORING</div>
                <h1>Overview</h1>
                <p className="page-description">Monitor your Raspberry Pi's health and performance.</p>
              </div>
              <div className="updated-label"><span className="status-dot" /> Awaiting backend connection</div>
            </section>

            <section className="metrics-grid" aria-label="System metrics">
              {metrics.map((metric) => (
                  <article className="metric-card" key={metric.label}>
                    <div className="metric-card-top">
                      <span className="metric-label">{metric.label}</span>
                      <span className={`metric-icon ${metric.tone}`}>{metric.icon}</span>
                    </div>
                    <div className="metric-value">{metric.value}<span>{metric.unit}</span></div>
                    <div className="metric-detail">{metric.detail}</div>
                  </article>
              ))}
            </section>

            <section className="content-grid" id="performance">
              <article className="panel performance-panel">
                <div className="panel-heading">
                  <div>
                    <h2>Performance</h2>
                    <p>Resource utilization over time</p>
                  </div>
                  <button className="select-button" type="button" disabled>Last 60 minutes⌄</button>
                </div>
                <div className="chart-placeholder">
                  <div className="chart-grid-lines"><span /><span /><span /><span /></div>
                  <div className="chart-empty">
                    <span className="chart-empty-icon">⌁</span>
                    <strong>No performance data yet</strong>
                    <span>Metrics will appear here when the API is connected.</span>
                  </div>
                  <div className="chart-axis"><span>60 min ago</span><span>30 min ago</span><span>Now</span></div>
                </div>
                <div className="chart-legend">
                  <span><i className="legend-dot cpu-dot" /> CPU</span>
                  <span><i className="legend-dot memory-dot" /> Memory</span>
                </div>
              </article>

              <article className="panel services-panel" id="services">
                <div className="panel-heading">
                  <div>
                    <h2>Service health</h2>
                    <p>Connected infrastructure</p>
                  </div>
                  <span className="service-count">0/3</span>
                </div>
                <div className="service-list">
                  {services.map((service) => (
                      <div className="service-row" key={service.name}>
                        <div className="service-symbol">◇</div>
                        <div className="service-info">
                          <strong>{service.name}</strong>
                          <span>{service.description}</span>
                        </div>
                        <span className="service-status">{service.status}</span>
                      </div>
                  ))}
                </div>
                <div className="panel-footer">Connection status will be detected from the backend.</div>
              </article>
            </section>

            <section className="panel system-panel" id="network">
              <div className="panel-heading">
                <div>
                  <h2>System information</h2>
                  <p>Hardware and operating system details</p>
                </div>
                <span className="placeholder-badge">Awaiting data</span>
              </div>
              <div className="system-info-grid">
                <div><span>Hostname</span><strong>—</strong></div>
                <div><span>Operating system</span><strong>—</strong></div>
                <div><span>CPU architecture</span><strong>—</strong></div>
                <div><span>Network interface</span><strong>—</strong></div>
              </div>
            </section>

            <footer className="page-footer">
              <span>Pi Control Center <span className="footer-separator">·</span> System dashboard</span>
              <span>REST API <span className="footer-separator">·</span> No WebSockets</span>
            </footer>
          </div>
        </main>
      </div>
  )
}

export default App
