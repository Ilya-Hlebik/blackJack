module.exports = {
  productionSourceMap: false,
  devServer : {
    port: 8090,
    disableHostCheck: true,
  },
  chainWebpack: (config) => {
    config.resolve.alias
      .set('@', require('path').resolve(__dirname, 'src'));
  }
};
