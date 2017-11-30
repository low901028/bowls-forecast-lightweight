import featuretools as ft
from featuretools.primitives.aggregation_primitives import Count
from featuretools.primitives.transform_primitive import Month

if __name__ == '__main__':
    es = ft.load_mock_customer(return_entityset=True)
    #es.get_all_instances("customers")

    # 使用dfs
    feature_matrix, feature_defs = ft.dfs(entityset=es,
                                          target_entity="customers",
                                          agg_primitives=[Count],
                                          trans_primitives=[Month],
                                          max_depth=1)
    print(feature_matrix)


