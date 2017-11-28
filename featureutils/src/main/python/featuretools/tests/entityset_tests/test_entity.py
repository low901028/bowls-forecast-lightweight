import pytest

from featuretools.tests.testing_utils.mock_ds import make_ecommerce_entityset

from featuretools import variable_types


@pytest.fixture
def es():
    return make_ecommerce_entityset()


def test_enforces_variable_id_is_str(es):
    assert variable_types.Categorical("1", es["customers"])
    with pytest.raises(AssertionError):
        variable_types.Categorical(1, es["customers"])

if __name__ == '__main__':
    es = make_ecommerce_entityset()
    assert variable_types.Categorical("1", es["customers"])
    with pytest.raises(AssertionError):
        variable_types.Categorical(1, es["customers"])

        # import pandas as pd
        # read_csv = pd.read_csv
        # csv_path =""
        # parse_dates =""
        # df = read_csv(csv_path, low_memory=False,
        #               parse_dates=parse_dates,
        #               compression=compression,
        #               usecols=use_variables,
        #               encoding=encoding,
        #               **kwargs)