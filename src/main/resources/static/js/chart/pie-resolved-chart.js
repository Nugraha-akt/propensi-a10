document.addEventListener('DOMContentLoaded', () => {
    const fetchData = () => {
        fetch('/api/v1/komplain/resolved-count-chart')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch data');
                }
                return response.json();
            })
            .then(data => {
                const chart = echarts.init(document.querySelector('#donutComplaintChart'));
                const chartData = Object.entries(data).map(([name, value]) => ({ name, value }));

                const option = {
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        top: '5%',
                        left: 'center'
                    },
                    series: [{
                        name: 'Status',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '18',
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: chartData
                    }]
                };

                chart.setOption(option);
            })
            .catch(error => {
                console.error(error);
                // Handle error condition
            });
    };

    fetchData();
});
